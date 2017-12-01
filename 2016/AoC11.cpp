#include <map>
#include <regex>
#include <string>
#include <iostream>
#include <cstdlib>
#include <cstdint>

#define UP 0
#define DOWN 1
#define GENERATOR 0
#define MICROCHIP 1
#define BOTTOM_FLOOR 0
#define TOP_FLOOR    3

// [this_floor][up_down][generator_or_microchip x other_floor]
//
// Returns a delta value for the items state.  For example:
//     items = items0 + move_table[0][UDGMO(UP,MICROCHIP,3)]
// Moves a MICROCHIP (whose GENERATOR is on floor 3) UP from floor 0 to 1
//
// If this is an illegal move (e.g. trying to go DOWN from floor 0), the
// delta will be 0x8888888888888888.  Attempting to move a nonexistent
// item will subtract from zero, causing one or more "8" bits to be set.
static uint64_t move_table[4][16];

// Convert a Generator/Microchip floor number pair to items state number
static uint64_t GM(uint64_t g, uint64_t m)
{
    return (uint64_t) 1 << (g << 4) << (m << 2);
}

// Test if this was the result of a legal move
static bool legal_move(uint64_t gm)
{
    return !(gm & 0x8888888888888888);
}

// Test if this configuration of items is compatible
static bool compatible_items(uint64_t gm)
{
    return !(((gm & 0x000000000000ffff) && (gm & 0x000f000f000f0000)) |
             ((gm & 0x00000000ffff0000) && (gm & 0x00f000f0000000f0)) |
             ((gm & 0x0000ffff00000000) && (gm & 0x0f0000000f000f00)) |
             ((gm & 0xffff000000000000) && (gm & 0x0000f000f000f000)));
}

// Returns a (up_down x generator_or_microchip x other_floor) index for move_table
static int UDGMO(int ud, int gm, int o)
{
    return (ud << 3) | (gm << 2) | o;
}

// Initialize the move table
static void init_move_table()
{
    // Default all entries to 0x8888888888888888
    memset(move_table, 0x88, sizeof(move_table));

    // Generate all legal moves
    for (int e = BOTTOM_FLOOR; e <= TOP_FLOOR; e++) {
        for (int o = 0; o < 4; o++) {
            if (e > BOTTOM_FLOOR) {
                move_table[e][UDGMO(DOWN,GENERATOR,o)] = GM(e - 1, o) - GM(e, o);
                move_table[e][UDGMO(DOWN,MICROCHIP,o)] = GM(o, e - 1) - GM(o, e);
            }

            if (e < TOP_FLOOR) {
                move_table[e][UDGMO(UP,GENERATOR,o)] = GM(e + 1, o) - GM(e, o);
                move_table[e][UDGMO(UP,MICROCHIP,o)] = GM(o, e + 1) - GM(o, e);
            }
        }
    }
}

// Sign of depth (-1 if negative, 1 otherwise)
static int8_t depth_sign(int8_t depth)
{
    return (depth >= 0) - (depth < 0);
}

struct state_t {
    uint64_t items; // items equivalence class index
    uint8_t  e;     // elevator level

    state_t(uint64_t items, uint8_t e) : items(items), e(e) { }
    bool operator<(const state_t &o) const {
        return (items == o.items) ? (e < o.e) : (items < o.items);
    }
};

static int8_t solve(const state_t &start, const state_t &end)
{
    std::map<state_t,int8_t> memo;
    memo.emplace(start, 0);
    memo.emplace(end, -1);

    // Breadth-first search forward and backward
    for (int8_t depth = 0; depth >= 0; depth++) {
        for (auto mi0 : memo) {
            // Only examine items at the current depth
            if (abs(mi0.second) != depth) {
                continue;
            }

            const state_t &state0 = mi0.first;

            // Select first item class to move
            for (int m0 = 0; m0 < 16; m0++) {
                // Apply move, prune if illegal
                uint64_t items1 = state0.items + move_table[state0.e][m0];
                if (!legal_move(items1)) {
                    continue;
                }

                // Select second item class to move (same up/down direction);
                // (m1 == -1 means don't move a second item)
                uint8_t e = state0.e - ((m0 >> 2) & 2) + 1;
                for (int m1 = -1; m1 < 8; m1++) {
                    uint64_t items2 = items1;
                    if (m1 >= 0) {
                        items2 += move_table[state0.e][m1 | (m0 & 8)];
                    }
                    // Prune if illegal move, or items not compatible
                    if (!legal_move(items2) || !compatible_items(items2)) {
                        continue;
                    }

                    // Check if the new state has been seen before
                    auto mi = memo.find(state_t(items2, e));
                    if (mi == memo.end()) {
                        // Nope, increment depth and add to memo
                        memo.emplace(state_t(items2, e), mi0.second + depth_sign(mi0.second));
                    } else if (depth_sign(mi0.second) != depth_sign(mi->second)) {
                        // Yes, and signs were opposite (solved; met in the middle)
                        return abs(mi0.second) + abs(mi->second);
                    } // Otherwise prune
                }
            }
        }
    }

    return -1;
}

static std::map<std::string,std::pair<uint64_t,uint64_t>> parse_input(std::istream &in)
{
    std::map<std::string,std::pair<uint64_t,uint64_t>> elements;

    static const std::regex re("a (\\w+)( generator|-compatible microchip)");
    for (uint64_t e = BOTTOM_FLOOR; e <= TOP_FLOOR; e++) {
        std::string line;
        getline(in, line);
        std::sregex_iterator next(line.begin(), line.end(), re), end;
        while (next != end) {
            auto ei = elements.find(next->str(1));
            if (ei == elements.end()) {
                elements.emplace(next->str(1), std::make_pair(e, e));
            } else if (next->str(2)[1] == 'g') {
                ei->second.first = e;
            } else {
                ei->second.second = e;
            }
            next++;
        }
    }

    return elements;
}

int main(void)
{
    // Read in the input
    state_t start(0, BOTTOM_FLOOR), goal(0, TOP_FLOOR);
    for (auto ei : parse_input(std::cin)) {
        start.items += GM(ei.second.first, ei.second.second);
        goal.items += GM(TOP_FLOOR, TOP_FLOOR);
    }

    init_move_table();

    for (int i = 1; i <= 2; i++) {
        std::cout << "Part " << i << ": " << (int) solve(start, goal) << "\n";
        // Add two mated pairs to the bottom floor and extend the goal
        start.items += 2 * GM(BOTTOM_FLOOR, BOTTOM_FLOOR);
        goal.items  += 2 * GM(TOP_FLOOR, TOP_FLOOR);
    }

    return 0;
}
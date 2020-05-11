package blokus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PieceList {

    private static final int[][][] PIECES = {
            {
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 1, 1, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 1, 1},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 0},
                    {0, 1, 1, 1, 0},
                    {0, 0, 0, 1, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 0},
                    {0, 1, 1, 1, 0},
                    {0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 1, 1, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 0},
                    {0, 0, 1, 1, 0},
                    {0, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 0},
                    {0, 0, 0, 1, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            }
    };

    private static final int[][][][] FLIPPED = new int[21][4][5][5];
    private static final int[][][][] NOT_FLIPPED = new int[21][4][5][5];

    public static class Layout {
        int pieceId;
        boolean flipped;
        int rotation;

        public Layout(int pieceId, boolean flipped, int rotation) {
            this.pieceId = pieceId;
            this.flipped = flipped;
            this.rotation = rotation;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Layout)) {
                return false;
            }

            Layout layout = (Layout)obj;

            return matrixEquals(GET_PIECE(pieceId, flipped, rotation), GET_PIECE(layout.pieceId, layout.flipped, layout.rotation));
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(GET_PIECE(pieceId, flipped, rotation));
        }
    }

    public final static List<Layout> UNIQUE_LAYOUTS;

    static {
        /*
         * First, without flipping, perform all rotations
         */
        for(int i = 0; i < 21; i++) {
            NOT_FLIPPED[i][0] = PIECES[i];
            NOT_FLIPPED[i][1] = rotate(PIECES[i]);
            NOT_FLIPPED[i][2] = rotate(NOT_FLIPPED[i][1]);
            NOT_FLIPPED[i][3] = rotate(NOT_FLIPPED[i][2]);
        }

        /*
         * Next, flip, first, then perform all rotations
         */
        for(int i = 0; i < 21; i++) {
            FLIPPED[i][0] = mirror(NOT_FLIPPED[i][0]);
            FLIPPED[i][1] = mirror(NOT_FLIPPED[i][1]);
            FLIPPED[i][2] = mirror(NOT_FLIPPED[i][2]);
            FLIPPED[i][3] = mirror(NOT_FLIPPED[i][3]);
        }

        // Compute all possible
        ArrayList<Layout> possible = new ArrayList<>();

        for(int piece = 0; piece < 21; piece++) {
            for(int rotation = 0; rotation < 4; rotation++) {
                possible.add(new Layout(piece, false, rotation));
                possible.add(new Layout(piece, true, rotation));
            }
        }

        int c = 0;

        // Remove duplicates
//        for(int i = 0; i < 21; i++) {
//            for(int j = 0; j < 21; j++) {
//                if(i == j) continue;
//
//                if(possible.get(i).equals(possible.get(j))) {
//                    possible.remove(j);
//                    i = 0;
//                    j = 0;
//                    //System.out.println("REEE");
//                    c++;
//                }
//            }
//        }

        UNIQUE_LAYOUTS = possible.stream().distinct().collect(Collectors.toList());

//        for(int z = 0; z < 4; z++) {
//            for (int i = 0; i < 5; i++) {
//                for (int j = 0; j < 5; j++) {
//                    System.out.print(FLIPPED[2][z][i][j]);
//                    //System.out.print(rotate(PIECES[0])[j][i]);
//                }
//                System.out.println();
//            }
//            System.out.println();
//            System.out.println();
//            System.out.println();
//        }
    }

    public static int[][] GET_PIECE(int id, boolean flip, int rotation) {
        if(!flip && rotation == 0) {
            return PIECES[id];
        } else if(flip) {
            return FLIPPED[id][rotation];
        } else {
            return NOT_FLIPPED[id][rotation];
        }
    }


    private static boolean matrixEquals(int[][] matrix1, int[][] matrix2) {
        for(int row = 0; row < 5; row++) {
            for(int col = 0; col < 5; col++) {
                if(matrix1[row][col] != matrix2[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }

    private static int[][] rotate(int[][] matrix) {
        int N = 5;

        int[][] mat = new int[N][N];

        for(int row = 0; row < N; row++) {
            System.arraycopy(matrix[row], 0, mat[row], 0, N);
        }

        // Transpose the matrix
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                int temp = mat[i][j];
                mat[i][j] = mat[j][i];
                mat[j][i] = temp;
            }
        }

        // swap columns
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N / 2; j++) {
                int temp = mat[i][j];
                mat[i][j] = mat[i][N - j - 1];
                mat[i][N - j - 1] = temp;
            }
        }

        return mat;
    }

    private static int[][] mirror(int[][] matrix) {
        int N = 5;

        int[][] out = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                out[i][N - j - 1] = matrix[i][j];
            }
        }
        return out;
    }


}

//package com.mygdx.game;
//
//import com.badlogic.gdx.graphics.Color;
//import com.mygdx.game.Variables;
//import org.mariuszgromada.math.mxparser.Function;
//
//import java.util.concurrent.ThreadLocalRandom;
//
//import static com.badlogic.gdx.math.MathUtils.random;
//
////https://xoppa.github.io/blog/interacting-with-3d-objects/
//
//public class TerrainChunk {
//
//    float[] vertices; // The verticies which create elevation
//    short[] indices; // Indinces are the same ideas as above
//
//    int vertexSize;
//    int positionSize = 3;
//
//    // int mapSize = Variables.getMapSize();        // Variables
//
//    Function mapFunction;
//
//    boolean sand;
//
//    public static int [][] sandInfo;
//
//    /**
//     * Terrainchunk constructor to receive the determine height & width by user and other parameters
//     * @param function represents what the actual function will look like and is determined in settings by the user
//     */
//    public TerrainChunk(Function function) {
//
//        if ((mapSize + 1) * (mapSize + 1) > Short.MAX_VALUE) {
//            throw new IllegalArgumentException(
//                    "Chunk size too big, (width + 1)*(height+1) must be <= 32767");
//        }
//        // position, normal, color, texture
//        int vertexSize = 3 + 3 + 1 + 2;
//
//        this.mapFunction = function;
//        this.vertices = new float[(mapSize + 1) * (mapSize + 1) * vertexSize];
//        this.indices = new short[mapSize * mapSize * 6];
//        this.vertexSize = vertexSize;
//        this.sand = sand;
//
//
//        buildVertices();
//        buildIndices();
//
//
//        calcNormals(indices, vertices);
//
//    }
//
//
//    /**
//     * This builds the vertices which pretty much means the elevation on the map
//     */
//    public void buildVertices() {
//        int heightPitch = mapSize + 1;
//        int widthPitch = mapSize + 1;
//
//        int idx = 0;
//
//        float scale = 1f;
//
//        for (int z = 0; z < heightPitch; z++) {
//            for (int x = 0; x < widthPitch; x++) {
//                // POSITION
//                vertices[idx++] = scale * x;
//                vertices[idx++] = (float) (-mapFunction.calculate(z, x));
//                vertices[idx++] = scale * z;
//
//                // NORMAL, skip these for now
//                idx += 3;
//
//                // COLOR
//                vertices[idx++] = Color.WHITE.toFloatBits();
//
//                // TEXTURE
//                vertices[idx++] = (x / (float) mapSize);
//                vertices[idx++] = (z / (float) mapSize);
//            }
//        }
//    }
//
//    /**
//     * This method serves to build the indicies of the map
//     * this means the ends of the vertices
//     */
//    private void buildIndices() {
//        int idx = 0;
//        short pitch = (short) (mapSize + 1);
//        short i1 = 0;
//        short i2 = 1;
//        short i3 = (short) (1 + pitch);
//        short i4 = pitch;
//
//        short row = 0;
//
//        for (int z = 0; z < mapSize; z++) {
//            for (int x = 0; x < mapSize; x++) {
//                indices[idx++] = i1;
//                indices[idx++] = i2;
//                indices[idx++] = i3;
//
//                indices[idx++] = i3;
//                indices[idx++] = i4;
//                indices[idx++] = i1;
//
//                i1++;
//                i2++;
//                i3++;
//                i4++;
//            }
//
//            row += pitch;
//            i1 = row;
//            i2 = (short) (row + 1);
//            i3 = (short) (i2 + pitch);
//            i4 = (short) (row + pitch);
//        }
//    }
//
//    /**
//     * Gets the index of the first float of a normal for a specific vertex
//     * @param vertIndex represents the iterator for the vertices
//     */
//    private int getNormalStart(int vertIndex) {
//        return vertIndex * vertexSize + positionSize;
//    }
//
//    /**
//     * Gets the index of the first float of a specific vertex
//     * @param vertIndex receives the iterator for the vertices
//     * @return returns the total size, means the index multiplied by the total size
//     */
//    private int getPositionStart(int vertIndex) {
//        return vertIndex * vertexSize;
//    }
//
//
//    /**
//     * This method aims to add the provided value to the normal
//     * @param vertIndex receives the iterator
//     * @param verts the array of vertices
//     * @param x coordinate
//     * @param y coordinate
//     * @param z coordinate
//     */
//    private void addNormal(int vertIndex, float[] verts, float x, float y, float z) {
//
//        int i = getNormalStart(vertIndex);
//
//        verts[i] += x;
//        verts[i + 1] += y;
//        verts[i + 2] += z;
//    }
//
//    /**
//     * This method serves to create the normals to vertices
//     * @param vertIndex the iterator
//     * @param verts receives the array
//     */
//    private void normalizeNormal(int vertIndex, float[] verts) {
//
//        int i = getNormalStart(vertIndex);
//
//        float x = verts[i];
//        float y = verts[i + 1];
//        float z = verts[i + 2];
//
//        float num2 = ((x * x) + (y * y)) + (z * z);
//        float num = 1f / (float) Math.sqrt(num2);
//        x *= num;
//        y *= num;
//        z *= num;
//
//        verts[i] = x;
//        verts[i + 1] = y;
//        verts[i + 2] = z;
//    }
//
//    /**
//     * This method aims to calculate the normals to the field
//     * @param indices and the bases
//     * @param verts array
//     */
//    private void calcNormals(short[] indices, float[] verts) {
//
//        for (int i = 0; i < indices.length; i += 3) {
//            int i1 = getPositionStart(indices[i]);
//            int i2 = getPositionStart(indices[i + 1]);
//            int i3 = getPositionStart(indices[i + 2]);
//
//            // p1
//            float x1 = verts[i1];
//            float y1 = verts[i1 + 1];
//            float z1 = verts[i1 + 2];
//
//            // p2
//            float x2 = verts[i2];
//            float y2 = verts[i2 + 1];
//            float z2 = verts[i2 + 2];
//
//            // p3
//            float x3 = verts[i3];
//            float y3 = verts[i3 + 1];
//            float z3 = verts[i3 + 2];
//
//            // u = p3 - p1
//            float ux = x3 - x1;
//            float uy = y3 - y1;
//            float uz = z3 - z1;
//
//            // v = p2 - p1
//            float vx = x2 - x1;
//            float vy = y2 - y1;
//            float vz = z2 - z1;
//
//            // n = cross(v, u)
//            float nx = (vy * uz) - (vz * uy);
//            float ny = (vz * ux) - (vx * uz);
//            float nz = (vx * uy) - (vy * ux);
//
//            // normalize(n)
//            float num2 = ((nx * nx) + (ny * ny)) + (nz * nz);
//            float num = 1f / (float) Math.sqrt(num2);
//            nx *= num;
//            ny *= num;
//            nz *= num;
//
//            addNormal(indices[i], verts, nx, ny, nz);
//            addNormal(indices[i + 1], verts, nx, ny, nz);
//            addNormal(indices[i + 2], verts, nx, ny, nz);
//        }
//
//        for (int i = 0; i < (verts.length / vertexSize); i++) {
//            normalizeNormal(i, verts);
//        }
//    }
//
//}

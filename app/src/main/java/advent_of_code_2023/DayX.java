package advent_of_code_2023;

import java.util.HashSet;
import java.util.Objects;

public class DayX {

    static class Cube implements Comparable<Cube> {

        int x, xx, y, yy, z, zz;
        String name;

        public Cube() {

        }

        @Override
        public int compareTo(final Cube o) {
            return name.compareTo(o.name);
        }

        public void descend() {
            z--;
            zz--;
        }

        public void ascend() {
            z++;
            zz++;
        }

        Cube copy() {
            var c = new Cube();
            c.name = name;
            c.x = x;
            c.y = y;
            c.z = z;
            c.xx = xx;
            c.yy = yy;
            c.zz = zz;
            return c;
        }

        public boolean collides(Cube other) {
            var xO = (x >= other.x && x <= other.xx) || (other.x >= x && other.x <= xx);
            var yO = (y >= other.y && y <= other.yy) || (other.y >= y && other.y <= yy);
            var zO = (z >= other.z && z <= other.zz) || (other.z >= z && other.z <= zz);

            return other != this &&
                    !other.equals(this) &&
                    xO && yO && zO;

        }

        @Override
        public boolean equals(final Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            final Cube cube = (Cube) o;
            return x == cube.x && xx == cube.xx && y == cube.y && yy == cube.yy && z == cube.z && zz == cube.zz
                    && Objects.equals(name, cube.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, xx, y, yy, z, zz, name);
        }

        @Override
        public String toString() {
            return "Cube{" +
                    name + ", " +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", xx=" + xx +
                    ", yy=" + yy +
                    ", zz=" + zz +
                    '}';
        }
    }

    static int part1(String[] input) {
        var cubes = new HashSet<Cube>();
        var name = 'A';
        for (var line : input) {
            var parts = line.split("([,~])");
            //            System.out.println(Arrays.toString(parts));
            var cube = new Cube();
            cube.x = Integer.parseInt(parts[0]);
            cube.y = Integer.parseInt(parts[1]);
            cube.z = Integer.parseInt(parts[2]);
            cube.xx = Integer.parseInt(parts[3]);
            cube.yy = Integer.parseInt(parts[4]);
            cube.zz = Integer.parseInt(parts[5]);
            cube.name = "" + name;

            name++;
            cubes.add(cube);
        }

        var stable = false;
        while (!stable) {
            stable = true;

            for (var c : cubes) {
                var ok = true;
                if (c.z > 1) {
                    c.descend();
                    for (var cc : cubes) {
                        if (c.collides(cc)) {
                            ok = false;
                            break;
                        }
                    }

                    if (!ok) {
                        c.ascend();
                    } else {
                        stable = false;
                    }
                }
            }
        }

        int count = 0;

        for (var c : cubes) {
            var copy = new HashSet<>(cubes);
            copy.remove(c);

            var allOk = true;
            for (var cc : copy) {
                var ok = true;
                if (cc.z > 1) {
                    cc.descend();

                    for (var ccc : copy) {
                        if (cc.collides(ccc)) {
                            ok = false;
                            break;
                        }
                    }

                    cc.ascend();
                    if (ok) {
                        allOk = false;
                        break;
                    }
                }
            }

            if (allOk) {
                count++;
            }
        }

        return count;
    }

    static long part2(String[] input) {
        var cubes = new HashSet<Cube>();
        var name = 1;
        for (var line : input) {
            var parts = line.split("([,~])");
            //            System.out.println(Arrays.toString(parts));
            var cube = new Cube();
            cube.x = Integer.parseInt(parts[0]);
            cube.y = Integer.parseInt(parts[1]);
            cube.z = Integer.parseInt(parts[2]);
            cube.xx = Integer.parseInt(parts[3]);
            cube.yy = Integer.parseInt(parts[4]);
            cube.zz = Integer.parseInt(parts[5]);
            cube.name = "" + name;

            name++;
            cubes.add(cube);
        }

        var stable = false;
        while (!stable) {
            stable = true;

            for (var c : cubes) {
                var ok = true;
                if (c.z > 1) {
                    c.descend();
                    for (var cc : cubes) {
                        if (c.collides(cc)) {
                            ok = false;
                            break;
                        }
                    }

                    if (!ok) {
                        c.ascend();
                    } else {
                        stable = false;
                    }
                }
            }
        }

        long count = 0;

        for (var c : cubes) {
            var copy = HashSet.<Cube>newHashSet(cubes.size());

            cubes.forEach(cc -> {
                if (c != cc) {
                    copy.add(cc.copy());
                }
            });

            var fallen = new HashSet<String>();

            stable = false;
            while (!stable) {
                stable = true;

                for (var cc : copy) {
                    var ok = true;
                    if (cc.z > 1) {
                        cc.descend();
                        for (var ccc : copy) {
                            if (cc.collides(ccc)) {
                                ok = false;
                                break;
                            }
                        }

                        if (!ok) {
                            cc.ascend();
                        } else {
                            fallen.add(cc.name);
                            stable = false;
                        }
                    }
                }
            }

            count += fallen.size();
        }

        return count;
    }

}

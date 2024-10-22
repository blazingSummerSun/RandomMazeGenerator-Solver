package backend.academy.maze;

import java.util.Objects;

public class Node implements Comparable<Node> {
    public final int x;
    public final int y;
    final int g;
    final int h;
    Node parent;

    Node(int x, int y, int g, int h, Node parent) {
        this.y = y;
        this.x = x;
        this.g = g;
        this.h = h;
        this.parent = parent;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return x == node.x && y == node.y && g == node.g && h == node.h && Objects.equals(parent, node.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, g, h, parent);
    }

    int f() {
        return g + h;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.f(), other.f());
    }
}

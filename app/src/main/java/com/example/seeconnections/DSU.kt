package com.example.seeconnections

class DSU(val n: Int) {
    lateinit var parent: IntArray;
    lateinit var size: IntArray;

    init {
        parent = IntArray(n);
        size = IntArray(n);

        for (i in 0..n - 1) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    fun findParent(node: Int): Int {
        if (parent[node] == node)
            return node;

        parent[node] = findParent(parent[node])

        return parent[node]
    }

    fun union(u: Int, v: Int) {
        var pu: Int = findParent(u);
        var pv: Int = findParent(v);

        if (size[pu] > size[pv]) {
            parent[pv] = pu;
            size[pu] += size[pv];
        } else {
            parent[pu] = pv;
            size[pv] += size[pu];
        }
    }

    fun checkConnected(u: Int, v: Int): Boolean {
        var pu: Int = findParent(u);
        var pv: Int = findParent(v);

        return pu == pv
    }


}
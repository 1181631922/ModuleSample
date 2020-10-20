package com.fanyafeng.modules.startup

import java.util.ArrayDeque


/**
 * Author: fanyafeng
 * Date: 2020/9/27 14:45
 * Email: fanyafeng@live.cn
 * Description: 拓扑图
 */
fun main() {

    fun initMap(
        nodes: Array<TNode<Int>?>,
        list: Array<ArrayList<TNode<Int>>?>,
        inDegree: IntArray
    ) {
        list[1]!!.add(TNode(3))
        nodes[1]!!.next = list[1]!!
        inDegree[3]++
        list[2]!!.add(TNode(4));list[2]!!.add(TNode(6))
        nodes[2]!!.next = list[2]!!
        inDegree[4]++;inDegree[6]++
        list[3]!!.add(TNode(5))
        nodes[3]!!.next = list[3]!!
        inDegree[5]++
        list[4]!!.add(TNode(5));list[4]!!.add(TNode(6))
        nodes[4]!!.next = list[4]!!
        inDegree[5]++;inDegree[6]++
        list[5]!!.add(TNode(7))
        nodes[5]!!.next = list[5]!!
        inDegree[7]++
        list[6]!!.add(TNode(8))
        nodes[6]!!.next = list[6]!!
        inDegree[8]++
        list[7]!!.add(TNode(8))
        nodes[7]!!.next = list[7]!!
        inDegree[8]++
    }

    val nodeSize = 9
    //存储节点
    val nodes = arrayOfNulls<TNode<Int>>(nodeSize)
    //存储入度
    val inDegree = IntArray(nodeSize)
    //临时空间，为了存储指向的集合
    val list = arrayOfNulls<ArrayList<TNode<Int>>>(nodeSize + 1)

    (1 until nodeSize).forEach {
        nodes[it] = TNode(it)
        list[it] = ArrayList()
    }
    initMap(nodes, list, inDegree)

    val s1 = ArrayDeque<TNode<Int>>()
    (1 until nodeSize).forEach {
        if (inDegree[it] == 0) {
            s1.add(nodes[it])
        }
    }

    while (!s1.isEmpty()) {
        val n1 = s1.pop()
        println("n1:" + n1.value)

        val next = n1.next
        for (it in next.indices) {
            inDegree[next[it].value]--
            if (inDegree[next[it].value] == 0) {
                s1.add(nodes[next[it].value])
            }
        }
    }


}

class TNode<T>(var value: Int) {
    var next: ArrayList<TNode<T>> = ArrayList()
}
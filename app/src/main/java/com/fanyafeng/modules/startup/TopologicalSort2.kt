package com.fanyafeng.modules.startup

import java.util.*
import kotlin.collections.ArrayList

/**
 * Author: fanyafeng
 * Date: 2020/9/27 18:22
 * Email: fanyafeng@live.cn
 * Description:
 */
object TopologicalSort2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val nodes = arrayOfNulls<node>(9) //储存节点
        val a = IntArray(9) //储存入度
        val list = arrayOfNulls<ArrayList<Int>>(10) //临时空间，为了存储指向的集合
        for (i in 1..8) {
            nodes[i] = node(i)
            list[i] = ArrayList()
        }
        initmap(nodes, list, a)

        //主要流程
        val s1 = ArrayDeque<node>();
//        val s1 = Stack<node?>()
        for (i in 1..8) {
            if (a[i] == 0) {
                s1.add(nodes[i])
            }
        }
        while (!s1.isEmpty()) {
            val n1 = s1.pop() //抛出输出
            print(n1!!.value.toString() + " ")
            val next = n1.next
            for (i in next.indices) {
                a[next[i]]-- //入度减一
                if (a[next[i]] == 0) //如果入度为0
                {
                    s1.add(nodes[next[i]])
                }
            }
        }
    }

    private fun initmap(nodes: Array<node?>, list: Array<ArrayList<Int>?>, a: IntArray) {
        list[1]!!.add(3)
        nodes[1]!!.setNest(list[1]!!)
        a[3]++
        list[2]!!.add(4)
        list[2]!!.add(6)
        nodes[2]!!.setNest(list[2]!!)
        a[4]++
        a[6]++
        list[3]!!.add(5)
        nodes[3]!!.setNest(list[3]!!)
        a[5]++
        list[4]!!.add(5)
        list[4]!!.add(6)
        nodes[4]!!.setNest(list[4]!!)
        a[5]++
        a[6]++
        list[5]!!.add(7)
        nodes[5]!!.setNest(list[5]!!)
        a[7]++
        list[6]!!.add(8)
        nodes[6]!!.setNest(list[6]!!)
        a[8]++
        list[7]!!.add(8)
        nodes[7]!!.setNest(list[7]!!)
        a[8]++
    }

    internal class node(var value: Int) {
        var next: List<Int>
        fun setNest(list: ArrayList<Int>) {
            next = list
        }

        init {
            next = ArrayList()
        }
    }
}
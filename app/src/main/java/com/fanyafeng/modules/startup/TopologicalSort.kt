package com.fanyafeng.modules.startup


/**
 * Author: fanyafeng
 * Date: 2020/9/27 14:45
 * Email: fanyafeng@live.cn
 * Description: 拓扑图
 */
fun main() {

    //https://bkimg.cdn.bcebos.com/pic/9345d688d43f87940e886390da1b0ef41bd53a1c?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2U5Mg==,g_7,xp_5,yp_5
    val nodeE = TModel("e")

    val nodeD = TModel("d")

    val nodeF = TModel("f")

    val nodeB = TModel("b")

    val nodeC = TModel("c")

    val nodeA = TModel("a")

    val nodeAll = mutableListOf(nodeA, nodeB, nodeC, nodeD, nodeE, nodeF)

    nodeA.toList.apply {
        add(nodeB)
        add(nodeC)
        add(nodeD)
    }

    nodeB.fromList.apply {
        add(nodeA)
        add(nodeC)
    }

    nodeC.fromList.apply {
        add(nodeA)
    }
    nodeC.toList.apply {
        add(nodeE)
        add(nodeB)
    }

    nodeD.fromList.apply {
        add(nodeA)
        add(nodeF)
    }
    nodeD.toList.apply {
        add(nodeE)
    }

    nodeE.fromList.apply {
        add(nodeD)
        add(nodeC)
        add(nodeF)
    }

    nodeF.toList.apply {
        add(nodeD)
        add(nodeE)
    }


    fun <M> findFromList(node: TModel<M>): Boolean {
        print(node.me.toString())
        val fromList = node.fromList
        if (fromList.size > 0) {
            fromList.forEach {
                return findFromList(it)
            }
        }
        return false
    }

    nodeAll.forEach {
        findFromList(it)
        println()
    }

}

class TModel<M>(val me: M) {

    /**
     * 入列表，据此获取入度
     */
    val fromList: MutableList<TModel<M>> = mutableListOf()

    val toList: MutableList<TModel<M>> = mutableListOf()
}
package edu.spring.td1.models

data class Category(val libelle: String) {
    private val items = HashSet<Item>()

    fun add(item: String): Boolean = items.add(Item(item))

    fun removeItem(item: String) = items.remove(Item(item))


    fun addAll(vararg itemNames: String):Boolean{
        itemNames.forEach { add(it) }
        return true
    }
}
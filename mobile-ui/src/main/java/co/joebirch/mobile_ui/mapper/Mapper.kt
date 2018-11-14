package co.joebirch.mobile_ui.mapper

interface Mapper<out V, in D> {

    fun mapToView(type: D): V

}
package com.example.examenmoviles

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        val arregloBPokemon = arrayListOf<BPokemon>()
        init {
            //Entrenadores
            arregloBEntrenador.add(
                BEntrenador(1,"ASH","Liga lavanda")
            )
            arregloBEntrenador.add(
                BEntrenador(2,"BROKE","Liga roca")
            )
            //Pokemons
            arregloBPokemon.add(
                BPokemon(1,"Pikachu","electrico",1)
            )
            arregloBPokemon.add(
                BPokemon(2,"eve","aire",2)
            )
        }
    }
}
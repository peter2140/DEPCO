package org.depco.core

class BlockChain{
    fun save(block: Block):Boolean{
        return block.verify() && block.sealIsValid && block.voteIsValid
    }
}
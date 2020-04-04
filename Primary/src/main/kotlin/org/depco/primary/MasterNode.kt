package org.depco.primary

import org.depco.common.*
import org.depco.core.Block
import org.depco.core.BlockChain
import org.depco.core.NodeInstance
import org.depco.core.Vote

abstract class MasterNode: NodeInstance(){
    private val candidateBlocks = ArrayList<Block>()

    abstract val blockChain: BlockChain
    private lateinit var curBlock: Block
    fun voteForBlock(){
        val block = candidateBlocks.maxBy { it.priority }
            ?:return

        val signedData = sign(privateKey, block.hashCode)
        block.clerk.vote(signedData,publicKey)
    }

    @ExperimentalStdlibApi
    fun compete(block: Block){
        val hash = block.hashCode
        var round = 0
        while(true){
            val bytes = combineByteArray(hash, round)
            val signedData = sign(privateKey, bytes)
            val signedHash = hash(signedData)
            val zeros = leadingZeroCount(signedHash)

            if(network.clerkIsReady)
                break

            if(zeros < network.clerkZeros){
                round++
                continue
            }

            network.broadcast(hash,signedData,publicKey)
            break
        }
    }


    @ExperimentalStdlibApi
    fun onFinalBlockReceived(block: Block){
        if(blockChain.save(block))
            compete(block)
    }

    fun onBlockVoted(vote: Vote){
        val votes = curBlock.votes
        votes.add(vote)

        if(votes.count() >= network.clerkRequiredVotes){
            network.broadcastFinalBlock(curBlock)
            return
        }
    }

    fun onSealedBlockReceived(sealedBlock: Block){
        network.broadcastSealedBlock(sealedBlock)
    }
}
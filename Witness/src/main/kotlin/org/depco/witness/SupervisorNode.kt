package org.depco.witness

import org.depco.common.*
import org.depco.core.Block
import org.depco.core.NodeInstance


abstract class SupervisorNode : NodeInstance() {
    @ExperimentalStdlibApi
    fun sealBlock(block: Block) {
        if (!block.verify())
            return

        val signedData = sign(privateKey, block.hashCode)
        val hash = hash(signedData)
        val precedingZeroCount = leadingZeroCount(hash)
        if (precedingZeroCount <= network.sealPrecedingThreshold)
            return

        block.clerk.seal(signedData, publicKey)
    }
}

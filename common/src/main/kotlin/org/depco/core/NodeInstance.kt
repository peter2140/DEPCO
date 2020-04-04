package org.depco.core

abstract class NodeInstance: Node(){
    protected abstract val network: Network
    protected abstract val privateKey:ByteArray
}
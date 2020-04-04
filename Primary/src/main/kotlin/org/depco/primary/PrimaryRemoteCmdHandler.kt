package org.depco.primary

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.depco.network.RemoteCmd

class PrimaryRemoteCmdHandler : SimpleChannelInboundHandler<RemoteCmd>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: RemoteCmd?) {
        //receive remote cmd
        //what if an http request is coming? using a different port might be a better choice.
        //let cmd focus on cmd. and command channel must be setup with device id.
        //ignore the socket without device id.

        //a primary node need to wait for
        //*. witness random connections, for notification of candidate block.
        //*. other primary node connections, for broadcast. vote
        //*. http download request, get candidate block from primary.
        //   other primary will transfer the candidate block.
        //   transfer node can be awarded in next block. like witness.
        //*. upload to primary is easy. do not need send back the whole block, just the signed data and public key is good.
        //   even public key is not necessary in case of ECDSA.

        //dispatch the remote cmd. the system is cmd driven. can copy netty mode. or using async await.
        //cmd start a session, state machine or transaction, following cmd will change the state of the machine

        //can also be treat as event driven. using event bus is OK.
        //http server seems to be a good choice. request response can be finished in one communication

        //using cmd
    }

}

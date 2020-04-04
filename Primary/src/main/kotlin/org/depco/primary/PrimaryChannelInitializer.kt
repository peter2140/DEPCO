package org.depco.primary

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOutboundHandlerAdapter
import io.netty.channel.ChannelPromise
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpServerCodec
import org.depco.common.curTime
import org.depco.network.RemoteCmd
import org.depco.network.CmdCodec


class PrimaryChannelInitializer :  ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch1: SocketChannel?) {
        val pipe = ch1?.pipeline()
            ?: return

        //p2p and http server.
        //p2p to bootup.
        with(pipe) {
            addLast("cmdCodec", CmdCodec())
            addLast("httpCodec", HttpServerCodec())
            addLast("remoteCmdHandler",PrimaryRemoteCmdHandler())
            addLast("httpHandler",PrimaryHttpHandler())
            addLast("writeTimeRecorder",object: ChannelOutboundHandlerAdapter(){
                override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                    if(msg !is RemoteCmd)
                        ctx?.channel()?.lastSendTime = curTime()

                    super.write(ctx, msg, promise)
                }
            })
        }
    }
}

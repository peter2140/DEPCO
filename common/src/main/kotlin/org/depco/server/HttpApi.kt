package org.depco.server

import cn.ms668.common.server.PrHttpRequest
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.HttpMethod

abstract class HttpApi{
    var method: HttpMethod? = null
        private set

    abstract fun onRequest(ctx:ChannelHandlerContext, request: PrHttpRequest, user: User?)
    fun setMethod(method: String?) {
        if (method == null || method.isEmpty()
            || "GET" != method && "POST" != method) {
            this.method = null
            return
        }
        this.method = HttpMethod.valueOf(method)
    }

    fun canBeCalledByUser(user: User?): Boolean {
        return Role.canExecute(user,this)
    }
}

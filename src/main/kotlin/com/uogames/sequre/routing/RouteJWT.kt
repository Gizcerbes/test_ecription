package com.uogames.sequre.routing

import com.google.gson.Gson
import com.uogames.sequre.model.Wellknown
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.jwtWell(){



    get("/.well-known/jwks.json") {
        val e = Wellknown(
            keys = listOf(
                Wellknown.Key(
                    alg = "RS256",
                    kty = "RSA",
                    use = "sig",
                    n ="MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAtfJaLrzXILUg1U3N1KV8yJr92GHn5OtYZR7qWk1Mc4cy4JGjklYup7weMjBD9f3bBVoIsiUVX6xNcYIr0Ie0AQIDAQABAkEAg+FBquToDeYcAWBe1EaLVyC45HG60zwfG1S4S3IB+y4INz1FHuZppDjBh09jptQNd+kSMlG1LkAc/3znKTPJ7QIhANpyB0OfTK44lpH4ScJmCxjZV52mIrQcmnS3QzkxWQCDAiEA1Tn7qyoh+0rOO/9vJHP8U/beo51SiQMw0880a1UaiisCIQDNwY46EbhGeiLJR1cidr+JHl86rRwPDsolmeEF5AdzRQIgK3KXL3d0WSoS//K6iOkBX3KMRzaFXNnDl0U/XyeGMuUCIHaXv+n+Brz5BDnRbWS+2vkgIe9bUNlkiArpjWvX+2we",
                    e = "AQAB",
                    kid = "6f8856ed-9189-488f-9011-0ff4b6c08edc",
                    x5t = "NTY2MjBCNzQ1RTJPLzk3NzczRUNPO0E4NzE2MjcwOUFCRkUwRTUxNA",
                    x5c = listOf("Array")
                )
            )
        )
        call.respond(e)
    }

}
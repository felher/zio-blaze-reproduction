package org.felher.tmpW9zwNTRjTn

import zio._, zio.console._
import zio.interop.catz._

import org.http4s.HttpRoutes
import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import org.http4s.server.blaze._
import zio.interop.catz.implicits._
import org.http4s.server.Server
import zio.duration.Duration
import java.util.concurrent.TimeUnit

object Main extends zio.interop.catz.CatsApp {
  val http4sTaskDsl = Http4sDsl[Task]
  import http4sTaskDsl._


  def run(args: List[String]): ZIO[ZEnv, Nothing, Int] =
    for {
      s  <- server.useForever.fork
      _  <- ZIO.sleep(Duration(3, TimeUnit.SECONDS))
      _  <- if (args.contains("interrupt")) s.interrupt else UIO.unit
      _  <- if (args.contains("die")) UIO.die(new Throwable("end")) else UIO.unit
    } yield 0

  def server: ZManaged[Any, Throwable, Server[Task]] =
    ZManaged
      .make(
        BlazeServerBuilder[Task]
          .bindHttp(8745)
          .withHttpApp(helloWorldService.orNotFound)
          .allocated
      )(x =>
        UIO({println("Destroying")}) *>
        x._2.either *>
        UIO { println("Destroyed")})
      .map(_._1)

  val helloWorldService = HttpRoutes.of[Task] { case GET -> Root => Ok(s"Hello") }
}

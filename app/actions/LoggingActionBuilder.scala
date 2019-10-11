package actions

import play.api.Logger
import play.api.mvc.{ActionBuilder, BodyParser, Request, Result}

import scala.concurrent.{ExecutionContext, Future}

class LoggingActionBuilder[B](val bodyParser: BodyParser[B])
                             (implicit val ec: ExecutionContext) extends ActionBuilder[Request, B] {
  override def parser: BodyParser[B] = bodyParser

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    Logger.debug(s"Just received the following request: $request")
    block(request)
  }

  override protected def executionContext: ExecutionContext = ec
}

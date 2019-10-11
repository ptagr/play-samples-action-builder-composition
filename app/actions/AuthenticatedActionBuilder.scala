package actions

import play.api.Logger
import play.api.mvc.{ActionBuilder, BodyParser, Request, Result, WrappedRequest}

import scala.concurrent.{ExecutionContext, Future}

case class User(id: String, roles: List[String])

case class AuthenticatedUser[A](user: User, request: Request[A]) extends WrappedRequest(request)

class AuthenticatedActionBuilder[B](val bodyParser: BodyParser[B])
                             (implicit val ec: ExecutionContext) extends ActionBuilder[AuthenticatedUser, B] {
  override def parser: BodyParser[B] = bodyParser

  override def invokeBlock[A](request: Request[A], block: AuthenticatedUser[A] => Future[Result]): Future[Result] = {
    Logger.debug(s"Just authenticated the following request: $request")
    block(AuthenticatedUser(User("123", List("AdminRole")), request))
  }

  override protected def executionContext: ExecutionContext = ec
}
package actions

import play.api.Logger
import play.api.mvc.{ActionBuilder, BodyParser, Request, Result, Results}

import scala.concurrent.{ExecutionContext, Future}

class AdminAuthorizedActionBuilder [B](val bodyParser: BodyParser[B])
                                      (implicit val ec: ExecutionContext) extends ActionBuilder[AuthenticatedUser, B] {
  override def parser: BodyParser[B] = bodyParser

  override def invokeBlock[A](request: Request[A], block: AuthenticatedUser[A] => Future[Result]): Future[Result] = {
    request match {
      case authenticatedUser: AuthenticatedUser[A] if authenticatedUser.user.roles.contains("AdminRole") => {
        Logger.debug(s"Just authorized the following request: $request")
        block(authenticatedUser)
      }
      case _ => Future.successful(Results.Forbidden)
    }

  }

  override protected def executionContext: ExecutionContext = ec
}
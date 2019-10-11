package controllers

import actions.{AuthenticatedActionBuilder, AuthenticatedUser, AdminAuthorizedActionBuilder, LoggingActionBuilder}
import javax.inject._
import play.api._
import play.api.mvc.Security.AuthenticatedRequest
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  lazy val loggingActionBuilder = new LoggingActionBuilder(BodyParsers.parse.default)
  lazy val authenticatedActionBuilder = new AuthenticatedActionBuilder(BodyParsers.parse.default)
  lazy val adminAuthorizedActionBuilder = new AdminAuthorizedActionBuilder(BodyParsers.parse.default)

  lazy val loggingAdminAuthorizedActionBuilder = loggingActionBuilder.andThen(authenticatedActionBuilder).andThen(adminAuthorizedActionBuilder)
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = loggingAdminAuthorizedActionBuilder { implicit request: AuthenticatedUser[AnyContent] =>
    Logger.debug(s"user for the request is ${request.user.id} and has roles ${request.user.roles}")
    Ok(views.html.index())
  }
  
  def explore() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.explore())
  }
  
  def tutorial() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.tutorial())
  }
  
}

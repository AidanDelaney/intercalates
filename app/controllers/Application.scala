package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

object Application extends Controller {

  def index = Action { request =>
    request.contentType match {
      case Some("application/json") => Ok(Json.toJson(Map("Result" -> true)))
      case Some("text/html")        => Ok(views.html.index("Your new application is ready."))
      case Some(reqType)            => BadRequest("Requested type " + reqType + " was not recognised")
      case None                     => BadRequest("You must specify a content-type in the request")
    }
  }
  
}
package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

import com.codahale.jerkson.Json._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.`type`.TypeReference

import icircles.input._
import icircles.gui._
import icircles.abstractDescription._
import icircles.concreteDiagram._

object Application extends Controller {

  def index = Action { request =>
    request.contentType match {
      case Some("application/json") => doDrawing(request.body.asJson)
      case Some("text/html")        => Ok(views.html.index("Your new application is ready."))
      case Some(reqType)            => BadRequest("Requested type " + reqType + " was not recognised")
      case None                     => BadRequest("You must specify a content-type in the request")
    }
  }

  def doDrawing (json: Option [JsValue]) = {
    json match {
      case Some(o) => Ok(Json.toJson(Map ("Result" -> drawDiagram(o.toString))))
      case None    => BadRequest("No Json object passed to draw");
    }
  }

  def drawDiagram (json: String): String = {
    val mapper               = new ObjectMapper()
    val sad                  = mapper.readValue(json, classOf[AbstractDiagram])
    val abstractDescription  = sad.toAbstractDescription();
    val dc                   = new DiagramCreator(abstractDescription);
    val csg                  = new CirclesSVGGenerator(dc.createDiagram(200))
    return csg.toString()
  }
}
package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.libs.json._
import com.codahale.jerkson.Json._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.`type`.TypeReference
import icircles.input._
import icircles.gui._
import icircles.abstractDescription._
import icircles.concreteDiagram._

import play.api.libs.concurrent.Akka
import play.api.Play.current

import views._

object Application extends Controller {

  def index = Action { request =>
    request.contentType match {
      case Some("application/json") => 
          Async {
              Akka.future { doDrawing(request.body) }.map {result => Ok(result) }
              }
      // text/html, other or None
      case _ => Ok(html.index())
    }
  }

  def doDrawing (body: AnyContent) = {
    body.asJson match {
      case Some(o) => Json.toJson(Map ("Result" -> drawDiagram(o.toString)))
      case None    => Json.toJson(Map ("Result" -> "null"));
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
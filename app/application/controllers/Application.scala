package application.controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(ui.views.html.index("Your new application is ready."))
  }

}

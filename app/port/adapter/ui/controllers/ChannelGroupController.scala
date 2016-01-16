package port.adapter.ui.controllers

import domain.model.channel.ChannelGroupRepository
import play.api.libs.json._
import play.api.mvc._
import port.adapter.ui.json.ChannelGroupWriters._
import port.adapter.web.service.HttpChannelGroupRepository

class ChannelGroupController extends Controller {

  private val channelGroupRepository = newChannelGroupRepository()

  protected[this] def newChannelGroupRepository(): ChannelGroupRepository = new HttpChannelGroupRepository

  def list = Action {
    val groups = channelGroupRepository.allChannelGroup().getOrElse(Seq.empty)
    Ok(Json.toJson(groups))
  }

}

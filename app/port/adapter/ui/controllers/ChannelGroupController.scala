package port.adapter.ui.controllers

import domain.model.channel.ChannelGroupRepository
import play.api.libs.json._
import play.api.mvc._
import port.adapter.service.HttpChannelGroupRepository
import port.adapter.ui.json.ChannelGroupWriters._

class ChannelGroupController extends Controller {

  private val channelGroupRepository = newChannelGroupRepository()

  protected[this] def newChannelGroupRepository(): ChannelGroupRepository = new HttpChannelGroupRepository

  def list = Action {
    val groups = channelGroupRepository.allChannelGroup().getOrElse(Seq.empty)
    Ok(Json.toJson(groups))
  }

}

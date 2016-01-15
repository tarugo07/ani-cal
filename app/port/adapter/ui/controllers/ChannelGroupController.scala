package port.adapter.ui.controllers

import domain.model.channel.{ChannelGroup, ChannelGroupId, ChannelGroupRepository}
import play.api.libs.json._
import play.api.mvc._
import port.adapter.web.service.HttpChannelGroupRepository

class ChannelGroupController extends Controller {

  implicit val channelGroupIdWriters = new Writes[ChannelGroupId] {
    override def writes(id: ChannelGroupId): JsValue = Json.toJson(id.value)
  }

  implicit val channelGroupWriters = new Writes[ChannelGroup] {
    override def writes(channelGroup: ChannelGroup): JsValue = {
      Json.obj(
        "id" -> channelGroup.id,
        "name" -> channelGroup.name
      )
    }
  }

  private val channelGroupRepository = newChannelGroupRepository()

  protected[this] def newChannelGroupRepository(): ChannelGroupRepository = new HttpChannelGroupRepository

  def list = Action {
    val groups = channelGroupRepository.allChannelGroup().getOrElse(Seq.empty)
    Ok(Json.toJson(groups))
  }

}

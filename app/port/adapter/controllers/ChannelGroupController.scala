package port.adapter.controllers

import domain.model.channel.{ChannelGroup, ChannelGroupId}
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

  val channelGroupRepository = new HttpChannelGroupRepository

  def list = Action {
    val groups = channelGroupRepository.allChannelGroup()
    Ok(Json.toJson(groups))
  }

}

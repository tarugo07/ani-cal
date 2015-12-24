package domain.model.channel

import scala.util.Try

trait ChannelGroupRepository {

  def channelGroupOfId(id: ChannelGroupId): Try[ChannelGroup]

  def allChannelGroup(): Seq[ChannelGroup]

}

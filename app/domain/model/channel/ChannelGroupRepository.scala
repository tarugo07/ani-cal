package domain.model.channel

trait ChannelGroupRepository {

  def channelGroupOfId(id: ChannelGroupId): ChannelGroup

}

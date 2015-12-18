package domain.model.channel

import domain.model.Entity

case class ChannelGroup(id: ChannelGroupId, name: String, channelIds: Seq[ChannelId]) extends Entity

package domain.model.channel

import domain.model.Entity

case class Channel(channelId: ChannelId, channelName: String) extends Entity

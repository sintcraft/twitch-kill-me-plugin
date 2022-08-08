package com.github.sintcraft.plugins.twitchkillmeplugin.rewards;

public class RewardData {
  final private String channelName;
  final private String userName;
  final private String channelID;
  final int amount;

  public RewardData(String channelName, String userName, String channelID, int amount) {
    this.channelName = channelName;
    this.userName = userName;
    this.channelID = channelID;
    this.amount = amount;
  }
}

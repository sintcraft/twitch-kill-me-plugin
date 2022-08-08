package com.github.sintcraft.plugins.twitchkillmeplugin;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.sintcraft.plugins.twitchkillmeplugin.events.RewardManager;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;

public class TwitchService {
  private OAuth2Credential credential;
  private TwitchKillMePlugin twitchKillMePlugin;
  private TwitchClient client;

  public TwitchService(TwitchKillMePlugin plugin) {
    this.twitchKillMePlugin = plugin;
    credential = new OAuth2Credential("twitch", plugin.getConfig().getString("twitch.token"));
    client = startBot();
    registerListeners();
  }

  private TwitchClient startBot(){
    return TwitchClientBuilder.builder()
            .withEnablePubSub(true)
            .withEnableChat(true)
            .withChatAccount(credential)
            .withDefaultAuthToken(credential)
            .build();
  }

  private void registerListeners() {
    new RewardManager().registerListener(client, this.twitchKillMePlugin, this);
  }
  public TwitchClient getClient() {
    return client;
  }

  public OAuth2Credential getCredential() {
    return credential;
  }
}

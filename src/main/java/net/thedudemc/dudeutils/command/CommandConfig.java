package net.thedudemc.dudeutils.command;

public class CommandConfig {
//	private static final List<String> initial = ImmutableList.of("save", "get", "set");
//	private static final List<String> settings = ImmutableList.of("CHAT_ENABLE_COLOR", "PORTAL_ENABLE_CALC", "PORTAL_SPAWN_PARTICLES", "MAGNET_ENABLE", "MAGNET_SPEED", "MAGNET_RANGE", "RECIPES_ENABLE_BLACKSTONE");
//	private static final List<String> booleans = ImmutableList.of("true", "false");
//	private static final List<String> numbers = ImmutableList.of("<number>");
//
//	public CommandConfig() {
//		DudeUtils.INSTANCE.getCommand("config").setExecutor(this);
//	}
//
//	@Override
//	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
//		if (command.getName().equalsIgnoreCase(("config"))) {
//			if (!sender.isOp()) {
//				sender.sendMessage(ChatColor.RED + "You are not allowed to run this command.");
//				return true;
//			}
//			if (args.length == 0) {
//				return false;
//			} else if (args.length == 1) {
//				if ("save".equalsIgnoreCase(args[0])) {
//					DudeConfig.setCurrentValues();
//					return true;
//				}
//			} else if (args.length == 2) {
//				if ("get".equalsIgnoreCase(args[0])) {
//					switch (args[1]) {
//					case "CHAT_ENABLE_COLOR":
//						sender.sendMessage(getValueString("CHAT_ENABLE_COLOR", DudeConfig.CHAT_ENABLE_COLOR));
//						break;
//					case "PORTAL_ENABLE_CALC":
//						sender.sendMessage(getValueString("PORTAL_ENABLE_CALC", DudeConfig.PORTAL_ENABLE_CALC));
//						break;
//					case "PORTAL_SPAWN_PARTICLES":
//						sender.sendMessage(getValueString("PORTAL_SPAWN_PARTICLES", DudeConfig.PORTAL_SPAWN_PARTICLES));
//						break;
//					case "MAGNET_ENABLE":
//						sender.sendMessage(getValueString("MAGNET_ENABLE", DudeConfig.MAGNET_ENABLE));
//						break;
//					case "MAGNET_SPEED":
//						sender.sendMessage(getValueString("MAGNET_SPEED", DudeConfig.MAGNET_SPEED));
//						break;
//					case "MAGNET_RANGE":
//						sender.sendMessage(getValueString("MAGNET_RANGE", DudeConfig.MAGNET_RANGE));
//						break;
//					case "RECIPES_ENABLE_BLACKSTONE":
//						sender.sendMessage(getValueString("RECIPES_ENABLE_BLACKSTONE", DudeConfig.RECIPES_ENABLE_BLACKSTONE));
//						break;
//					default:
//						return false;
//					}
//
//					return true;
//				}
//			} else if (args.length == 3) {
//				if ("set".equalsIgnoreCase(args[0])) {
//					switch (args[1]) {
//					case "CHAT_ENABLE_COLOR":
//						DudeConfig.CHAT_ENABLE_COLOR = Boolean.parseBoolean(args[2]);
//						sender.sendMessage(getValueString("CHAT_ENABLE_COLOR", DudeConfig.CHAT_ENABLE_COLOR));
//						break;
//					case "PORTAL_ENABLE_CALC":
//						DudeConfig.PORTAL_ENABLE_CALC = Boolean.parseBoolean(args[2]);
//						sender.sendMessage(getValueString("PORTAL_ENABLE_CALC", DudeConfig.PORTAL_ENABLE_CALC));
//						break;
//					case "PORTAL_SPAWN_PARTICLES":
//						DudeConfig.PORTAL_SPAWN_PARTICLES = Boolean.parseBoolean(args[2]);
//						sender.sendMessage(getValueString("PORTAL_SPAWN_PARTICLES", DudeConfig.PORTAL_SPAWN_PARTICLES));
//						if (DudeConfig.PORTAL_SPAWN_PARTICLES) {
//							PortalEvents.runParticleSpawner();
//						} else {
//							PortalEvents.cancelParticleSpawner();
//						}
//						break;
//					case "MAGNET_ENABLE":
//						DudeConfig.MAGNET_ENABLE = Boolean.parseBoolean(args[2]);
//						sender.sendMessage(getValueString("MAGNET_ENABLE", DudeConfig.MAGNET_ENABLE));
//						if (DudeConfig.MAGNET_ENABLE) {
//							MagnetHelper.runMagnet();
//						} else {
//							MagnetHelper.cancelMagnet();
//						}
//						break;
//					case "MAGNET_SPEED":
//						DudeConfig.MAGNET_SPEED = Double.parseDouble(args[2]);
//						sender.sendMessage(getValueString("MAGNET_SPEED", DudeConfig.MAGNET_SPEED));
//						break;
//					case "MAGNET_RANGE":
//						DudeConfig.MAGNET_RANGE = Integer.parseInt(args[2]);
//						sender.sendMessage(getValueString("MAGNET_RANGE", DudeConfig.MAGNET_RANGE));
//						break;
//					case "RECIPES_ENABLE_BLACKSTONE":
//						DudeConfig.RECIPES_ENABLE_BLACKSTONE = Boolean.parseBoolean(args[2]);
//						sender.sendMessage(getValueString("RECIPES_ENABLE_BLACKSTONE", DudeConfig.RECIPES_ENABLE_BLACKSTONE));
//						break;
//					default:
//						return false;
//					}
//					DudeConfig.setCurrentValues();
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	private String getValueString(String setting, Object value) {
//		StringBuilder sb = new StringBuilder();
//
//		sb.append(ChatColor.AQUA);
//		sb.append(setting);
//		sb.append(ChatColor.RESET);
//		sb.append(": ");
//		sb.append(ChatColor.GOLD);
//		sb.append(value);
//
//		return sb.toString();
//	}
//
//	@Override
//	public @Nullable List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
//		if (command.getName().equalsIgnoreCase("config")) {
//			if (args.length == 1) {
//				return StringUtil.copyPartialMatches(args[0], initial, new ArrayList<String>(initial.size()));
//			} else if (args.length == 2) {
//				if ("set".equalsIgnoreCase(args[0])) {
//					return StringUtil.copyPartialMatches(args[1], settings, new ArrayList<String>(settings.size()));
//				} else if ("get".equalsIgnoreCase(args[0])) {
//					return StringUtil.copyPartialMatches(args[1], settings, new ArrayList<String>(settings.size()));
//				}
//
//			} else if (args.length == 3) {
//				switch (args[1]) {
//				case "CHAT_ENABLE_COLOR":
//					return StringUtil.copyPartialMatches(args[2], booleans, new ArrayList<String>(booleans.size()));
//				case "PORTAL_ENABLE_CALC":
//					return StringUtil.copyPartialMatches(args[2], booleans, new ArrayList<String>(booleans.size()));
//				case "PORTAL_SPAWN_PARTICLES":
//					return StringUtil.copyPartialMatches(args[2], booleans, new ArrayList<String>(booleans.size()));
//				case "MAGNET_ENABLE":
//					return StringUtil.copyPartialMatches(args[2], booleans, new ArrayList<String>(booleans.size()));
//				case "MAGNET_SPEED":
//					return StringUtil.copyPartialMatches(args[2], numbers, new ArrayList<String>(numbers.size()));
//				case "MAGNET_RANGE":
//					return StringUtil.copyPartialMatches(args[2], numbers, new ArrayList<String>(numbers.size()));
//				case "RECIPES_ENABLE_BLACKSTONE":
//					return StringUtil.copyPartialMatches(args[2], booleans, new ArrayList<String>(booleans.size()));
//				default:
//					return null;
//				}
//			} else {
//				return null;
//			}
//		}
//		return null;
//	}

}

package org.pokemonium.server.constants;

public enum ClientPacket
{
	SERVER_NOTIFICATION(1),
	SERVER_ANNOUNCEMENT(2),
	START_TRADE(3),
	TRADE_OFFER(4),
	TRADE_OFFER_CANCEL(5),
	TRADE_ADD_POKE(6),
	TRADE_FINISHED(7),
	SWAP_PARTY(8),
	SHOP_LIST(9),
	NOT_ENOUGH_MONEY(10),
	POCKET_FULL(11),
	CANT_CARRY_MORE(12),
	DONT_HAVE_ITEM(13),
	BOUGHT_ITEM(14),
	SOLD_ITEM(15),
	SPRITE_SELECT(16),
	ACCESS_BOX(17),
	BATTLE_STARTED(18),
	ITEM_WON_BATTLE(19),
	NO_PP_LEFT(20),
	RECEIVE_POKE_DATA(21),
	ENEMY_TRAINER_NAME(22),
	MISC_BATTLE_MESSAGE(23),
	BATTLE_RESULT(24),
	POKEMON_FAINTED(25),
	MOVE_USED(26),
	MOVE_REQUESTED(27),
	EXP_GAINED(28),
	STATUS_RECEIVED(29),
	STATUS_REMOVED(30),
	SWITCH_REQUEST(31),
	SWITCHED_POKE(32),
	POKEMON_HP(33),
	BATTLE_RUN_EVENT(34),
	BATTLE_EARNINGS(35),
	POKE_LVL_UP(36),
	POKE_STATUS_UPDATE(37),
	POKE_LEAVE_PARTY(38),
	INIT_POKEMON(39),
	MOVE_LEARN_LVL(40),
	MOVE_LEARN_TM(41),
	POKE_EXP_GAINED(42),
	POKE_REQUEST_EVOLVE(43),
	POKE_LVL_CHANGE(44),
	POKE_HP_CHANGE(45),
	CANT_USE_ROD(46),
	CANT_FISH_LAND(47),
	FISH_GOT_AWAY(48),
	CAUGHT_NOTHING(49),
	CHAT_PACKET(50),
	TRAIN_LVL_LOW(51),
	BADGES_PACKET(52),
	SKILL_LVL_UP(53),
	RETURN_TO_LOGIN(54),
	MOVERETUTOR(55),
	SENDINFO(56),
	WEATHER_PACKET(57),
	POKES_HEALED(58),
	FACE_DOWN(59),
	FACE_LEFT(60),
	FACE_RIGHT(61),
	FACE_UP(62),
	SPRITE_CHANGE(63),
	UPDATE_COORDS(64),
	PLAYER_MOVEMENT(65),
	INIT_PLAYERS(66),
	ADD_PLAYER_MAP(67),
	INIT_FRIENDS(68),
	FRIEND_ADDED(69),
	FRIEND_REMOVED(70),
	REMOVE_PLAYER_MAP(71),
	SET_MAP_AND_WEATHER(72),
	PASS_CHANGE_RESULT(73),
	LOGIN_SUCCESS(74),
	LOGIN_FAILED(75),
	USER_OR_PASS_WRONG(76),
	ACC_SERVER_OFFLINE(77),
	SERVER_FULL(78),
	PLAYER_BANNED(79),
	UPDATE_ITEM_TOT(80),
	REMOVE_ITEM_BAG(81),
	PVP_PACKETS(82),
	TRADE_REQUEST(83),
	BATTLE_REQUEST(84),
	REQUEST_CANCELLED(85),
	REGISTER_SUCCESS(86),
	REGISTER_ISSUES(87),
	MONEY_CHANGED(88),
	POKE_CUR_PP(89),
	INIT_POKEDEX(90),
	UPDATE_POKEDEX(91),
	USE_ITEM(92),
	ALERT_MESSAGE(93),
	TRADE_OFF_INVALID(94),
	TRAVEL_BOAT(95),
	TRAVEL_EVENT(96),
	LOGGED_ELSEWHERE(97),
	CANT_USE_ITEM(98),
	BATTLEFRONTIER_EVENT(99),
	SERVER_REVISION(100),
	KURT(101),
	BATTLE_PP_UPDATE(102),
	PLAYER_RESYNC(103),
	PLAYER_TELEPORTBEHAVIOR(104),
	SERVER_BOADCASTSTATS(105),
	PLAYER_UPDATE_POKEMONFOLLOW(106),
	SHOWCLAN_WINDOW(107);
	

	private int code = -1;

	private ClientPacket(int code)
	{
		this.code = code;
	}

	public int getValue()
	{
		return code;
	}
}

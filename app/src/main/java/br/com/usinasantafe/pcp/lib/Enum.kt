package br.com.usinasantafe.pcp.lib

enum class StatusData { OPEN, CLOSE  }
enum class StatusSend { STARTED, SEND, SENDING, SENT }
enum class StatusForeigner { INSIDE, OUTSIDE }
enum class TypeMovEquip { INPUT, OUTPUT}
enum class TypeMovKey { REMOVE, RECEIPT }
enum class FlagUpdate { OUTDATED, UPDATED }
enum class TypeEquip { VEICULO, VEICULOSEG }
enum class TypeOcupante { MOTORISTA, PASSAGEIRO }
enum class TypeVisitTerc { VISITANTE, TERCEIRO }
enum class FlowApp { ADD, CHANGE }
enum class Errors { FIELD_EMPTY, TOKEN, UPDATE, EXCEPTION, INVALID }
enum class TypeButton { NUMERIC, CLEAN, OK, UPDATE }

enum class LevelUpdate { RECOVERY, CLEAN, SAVE, GET_TOKEN, SAVE_TOKEN, FINISH_UPDATE_INITIAL, FINISH_UPDATE_COMPLETED }


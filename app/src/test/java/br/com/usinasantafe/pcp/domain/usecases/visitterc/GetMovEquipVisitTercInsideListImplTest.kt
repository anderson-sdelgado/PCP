package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class GetMovEquipVisitTercInsideListImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val getMotoristaVisitTerc = mock<GetMotoristaVisitTerc>()
    private val usecase = IGetMovEquipVisitTercInsideList(
        movEquipVisitTercRepository = movEquipVisitTercRepository,
        getMotoristaVisitTerc = getMotoristaVisitTerc
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository ListOpenInput`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.listInside()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetMovEquipVisitTercInsideList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetTypeVisitTerc`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.listInside()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTerc(
                            idMovEquipVisitTerc = 1,
                            dthrMovEquipVisitTerc = Date(1723213270250),
                            tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                            veiculoMovEquipVisitTerc = "GOL",
                            placaMovEquipVisitTerc = "AAA-0000",
                            idVisitTercMovEquipVisitTerc = 1,
                            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                        )
                    )
                )
            )
            whenever(
                getMotoristaVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO,
                    idVisitTerc = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetMovEquipVisitTercInsideList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in GetMotorista`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.listInside()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTerc(
                            idMovEquipVisitTerc = 1,
                            dthrMovEquipVisitTerc = Date(1723213270250),
                            tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                            veiculoMovEquipVisitTerc = "GOL",
                            placaMovEquipVisitTerc = "AAA-0000",
                            idVisitTercMovEquipVisitTerc = 1,
                            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                getMotoristaVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO,
                    idVisitTerc = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetMovEquipVisitTercInsideList -> Unknown Error"
            )
        }

    @Test
    fun `Check return list model if GetMovEquipVisitTercInputOpenListImpl execute successfully`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val getMotoristaVisitTerc = mock<GetMotoristaVisitTerc>()
            whenever(
                movEquipVisitTercRepository.listInside()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTerc(
                            idMovEquipVisitTerc = 1,
                            dthrMovEquipVisitTerc = Date(1723213270250),
                            tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                            veiculoMovEquipVisitTerc = "GOL",
                            placaMovEquipVisitTerc = "AAA-0000",
                            idVisitTercMovEquipVisitTerc = 1,
                            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                getMotoristaVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO,
                    idVisitTerc = 1
                )
            ).thenReturn(
                Result.success(
                    "123.456.789-00 - Anderson"
                )
            )
            val usecase = IGetMovEquipVisitTercInsideList(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                getMotoristaVisitTerc = getMotoristaVisitTerc
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val mov = result.getOrNull()!![0]
            assertEquals(
                mov.veiculo,
                "GOL"
            )
            assertEquals(
                mov.placa,
                "AAA-0000"
            )
            assertEquals(
                mov.tipoVisitTerc,
                "TERCEIRO"
            )
            assertEquals(
                mov.motorista,
                "123.456.789-00 - Anderson"
            )
        }
}
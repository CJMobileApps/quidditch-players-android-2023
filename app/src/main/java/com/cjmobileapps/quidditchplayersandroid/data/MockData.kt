package com.cjmobileapps.quidditchplayersandroid.data

import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.Player
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.Position
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrappers
import com.cjmobileapps.quidditchplayersandroid.data.model.Status
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersEntities
import kotlinx.coroutines.CompletableDeferred
import retrofit2.Response
import java.net.HttpURLConnection
import java.util.UUID

object MockData {
    /*** houses data ***/

    val mockHouses =
        listOf(
            House(
                houseId = 0,
                name = HouseName.GRYFFINDOR,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/9/98/Gryffindor.jpg/revision/latest",
                emoji = "🦁",
            ),
            House(
                houseId = 1,
                name = HouseName.SLYTHERIN,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/6/6e/Slytherin.jpg/revision/latest",
                emoji = "🐍",
            ),
            House(
                houseId = 2,
                name = HouseName.RAVENCLAW,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/3/3c/RavenclawCrest.jpg/revision/latest",
                emoji = "🦅",
            ),
            House(
                houseId = 3,
                name = HouseName.HUFFLEPUFF,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/e/e4/Hufflepuff.jpg/revision/latest",
                emoji = "🦡",
            ),
        )

    val mockHousesResponseWrapper =
        ResponseWrapper(
            data = mockHouses,
            statusCode = HttpURLConnection.HTTP_OK,
        )

    val mockHousesResponseSuccess: Response<ResponseWrapper<List<House>>> =
        Response.success(mockHousesResponseWrapper)

    val mockHousesDeferredResponseSuccess = CompletableDeferred(mockHousesResponseSuccess)

    /*** players data ***/

    val mockRavenclawPlayersResponseWrapper =
        ResponseWrapper(
            data = ravenclawTeam(),
            statusCode = HttpURLConnection.HTTP_OK,
        )

    val mockRavenclawPlayersResponseSuccess = Response.success(mockRavenclawPlayersResponseWrapper)

    val mockRavenclawPlayersDeferredResponseSuccess =
        CompletableDeferred(
            mockRavenclawPlayersResponseSuccess,
        )

    /*** all players data ***/

    val mockAllQuidditchTeam =
        gryffindorTeam() + slytherinTeam() + ravenclawTeam() + hufflepuffTeam()

    private fun gryffindorTeam() =
        listOf(
            Player(
                id = UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b"),
                firstName = "Harry",
                lastName = "Potter",
                yearsPlayed =
                    listOf(
                        1991,
                        1992,
                        1993,
                        1994,
                        1995,
                        1996,
                        1997,
                    ),
                favoriteSubject = "Defense Against The Dark Arts",
                position = SEEKER,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/9/97/Harry_Potter.jpg/revision/latest?cb=20140603201724",
                house = HouseName.GRYFFINDOR,
            ),
            Player(
                id = UUID.fromString("ef55fa4b-b88f-4623-aaad-7abdcf2ea4f6"),
                firstName = "Katie",
                lastName = "Bell",
                yearsPlayed =
                    listOf(
                        1991,
                        1992,
                        1993,
                        1994,
                        1995,
                        1996,
                        1997,
                    ),
                favoriteSubject = "Transfiguration",
                position = CHASER,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/3/32/Katie_Bell_infobox.jpg/revision/latest?cb=20170118053940",
                house = HouseName.GRYFFINDOR,
            ),
            Player(
                id = UUID.fromString("c2e851f6-9400-4c9f-89aa-936dfc6de90c"),
                firstName = "Angelina",
                lastName = "Johnson",
                yearsPlayed =
                    listOf(
                        1990,
                        1991,
                        1992,
                        1993,
                        1994,
                        1995,
                        1996,
                    ),
                favoriteSubject = "Care of Magical Creatures",
                position = CHASER,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/2/24/GOF_promo_Angelina_Johnson.jpg/revision/latest?cb=20100522214321",
                house = HouseName.GRYFFINDOR,
            ),
            Player(
                id = UUID.fromString("841c567f-c8f8-4945-8401-ecb81e7219f2"),
                firstName = "Fred",
                lastName = "Weasley",
                yearsPlayed =
                    listOf(
                        1990,
                        1991,
                        1992,
                        1993,
                        1994,
                        1995,
                        1996,
                    ),
                favoriteSubject = "Charms",
                position = BEATER,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/9/90/Fred_HS_TDH_promo.jpg/revision/latest/scale-to-width-down/1000?cb=20230526122025",
                house = HouseName.GRYFFINDOR,
            ),
            Player(
                id = UUID.fromString("7891a848-883c-4925-aa43-a6fb620195fa"),
                firstName = "George",
                lastName = "Weasley",
                yearsPlayed =
                    listOf(
                        1990,
                        1991,
                        1992,
                        1993,
                        1994,
                        1995,
                        1996,
                    ),
                favoriteSubject = "Charms",
                position = BEATER,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/2/2a/DH_promo_front_closeup_George_Weasley.jpg/revision/latest?cb=20161119235305",
                house = HouseName.GRYFFINDOR,
            ),
            Player(
                id = UUID.fromString("b10e1a15-df78-47ab-94b6-78942437b1ad"),
                firstName = "Alicia",
                lastName = "Spinnet",
                yearsPlayed =
                    listOf(
                        1990,
                        1991,
                        1992,
                        1993,
                        1994,
                        1995,
                        1996,
                    ),
                position = CHASER,
                favoriteSubject = "Charms",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/7/7a/Alicia_Spinnet.png/revision/latest?cb=20160720064800",
                house = HouseName.GRYFFINDOR,
            ),
            Player(
                id = UUID.fromString("a04e1b6f-9b7f-407e-8beb-aaf7b8d34655"),
                firstName = "Oliver",
                lastName = "Wood",
                yearsPlayed =
                    listOf(
                        1989,
                        1990,
                        1991,
                        1992,
                        1993,
                        1994,
                    ),
                position = KEEPER,
                favoriteSubject = "Potions",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/2/2f/Oliver_WoodDH2.jpg/revision/latest?cb=20110801072255",
                house = HouseName.GRYFFINDOR,
            ),
        )

    private fun slytherinTeam() =
        listOf(
            Player(
                id = UUID.fromString("f5272335-7f6f-4aea-b0ba-c5c5dcec4aa5"),
                firstName = "Draco",
                lastName = "Malfoy",
                yearsPlayed =
                    listOf(
                        1992,
                        1993,
                        1994,
                        1995,
                        1996,
                        1997,
                    ),
                position = SEEKER,
                favoriteSubject = "Potions",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/7/7e/Draco_Malfoy_TDH.png/revision/latest/scale-to-width-down/1000?cb=20180116013508",
                house = HouseName.SLYTHERIN,
            ),
            Player(
                id = UUID.fromString("d86096a5-9d9b-4dc6-b830-1de5431a1f37"),
                firstName = "Miles",
                lastName = "Bletchley",
                yearsPlayed =
                    listOf(
                        1991,
                        1992,
                        1993,
                        1994,
                        1995,
                        1996,
                    ),
                position = KEEPER,
                favoriteSubject = "Study of Ancient Runes",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/4/40/Miles_Bletchley.jpeg/revision/latest?cb=20110810003628",
                house = HouseName.SLYTHERIN,
            ),
            Player(
                id = UUID.fromString("1dd6f764-365f-4013-8bc6-cacab0f45232"),
                firstName = "Gregory",
                lastName = "Goyle",
                yearsPlayed =
                    listOf(
                        1995,
                        1996,
                        1997,
                    ),
                position = BEATER,
                favoriteSubject = "Potions",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/e/e7/Gregory_Goyle_DH2.jpg/revision/latest?cb=20180306163743",
                house = HouseName.SLYTHERIN,
            ),
            Player(
                id = UUID.fromString("87ca2176-a15e-400e-98c2-21a4b7b34785"),
                firstName = "Vincent",
                lastName = "Crabbe",
                yearsPlayed =
                    listOf(
                        1995,
                        1996,
                        1997,
                    ),
                position = BEATER,
                favoriteSubject = "Potions",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/b/ba/Vincent_Crabbe.jpg/revision/latest/scale-to-width-down/1000?cb=20091224183746",
                house = HouseName.SLYTHERIN,
            ),
            Player(
                id = UUID.fromString("add49a74-db89-4c8b-bbcb-89be313442f7"),
                firstName = "Cassius",
                lastName = "Warrington",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                        1995,
                        1996,
                    ),
                position = CHASER,
                favoriteSubject = "History of Magic",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/0/08/Cassius_Warrington_OOTPF.bmp/revision/latest/thumbnail/width/360/height/360?cb=20130416151820",
                house = HouseName.SLYTHERIN,
            ),
            Player(
                id = UUID.fromString("498810f5-e1b1-47ff-865a-22ef7ff72c69"),
                firstName = "Adrian",
                lastName = "Pucey",
                yearsPlayed =
                    listOf(
                        1995,
                        1996,
                    ),
                position = CHASER,
                favoriteSubject = "Magical Theory",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/1/13/Adrianpucey-HS.jpg/revision/latest?cb=20101126164937",
                house = HouseName.SLYTHERIN,
            ),
            Player(
                id = UUID.fromString("627efe48-7a10-45ce-b64c-2027926dd71e"),
                firstName = "Graham",
                lastName = "Montague",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                        1995,
                        1996,
                    ),
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/c/c3/Graham_montague.jpg/revision/latest?cb=20140701101409&path-prefix=fr",
                position = CHASER,
                favoriteSubject = "Transfiguration",
                house = HouseName.SLYTHERIN,
            ),
        )

    fun ravenclawTeam() =
        listOf(
            Player(
                id = UUID.fromString("aa7fb66e-827f-42db-9aac-974c87b35504"),
                firstName = "Cho",
                lastName = "Chang",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                        1995,
                        1996,
                    ),
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/1/1e/Cho_Chang.jpg/revision/latest?cb=20180322164130",
                position = SEEKER,
                house = HouseName.RAVENCLAW,
                favoriteSubject = "Apparition",
            ),
            Player(
                id = UUID.fromString("ef968277-e996-4eca-8f94-1928dde4a979"),
                firstName = "Grant",
                lastName = "Page",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/9/93/GrantPage.png/revision/latest?cb=20130320232028",
                position = KEEPER,
                favoriteSubject = "Charms",
                house = HouseName.RAVENCLAW,
            ),
            Player(
                id = UUID.fromString("cdf95045-8df9-4609-bb26-5d4752823022"),
                firstName = "Duncan",
                lastName = "Inglebee",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/2/29/Dinglebee.png/revision/latest?cb=20140827133418",
                position = BEATER,
                favoriteSubject = "Astronomy",
                house = HouseName.RAVENCLAW,
            ),
            Player(
                id = UUID.fromString("870d5078-584d-4d34-9ff9-303db6c03992"),
                firstName = "Jason",
                lastName = "Samuels",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                position = BEATER,
                favoriteSubject = "Transfiguration",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/1/1b/Jasonsamuelsqwc.png/revision/latest?cb=20140827133708",
                house = HouseName.RAVENCLAW,
            ),
            Player(
                id = UUID.fromString("8726e642-65a9-4dd7-b8eb-08f2a5850f4d"),
                firstName = "Randolph",
                lastName = "Burrow",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                position = CHASER,
                favoriteSubject = "Advanced Arithmancy Studies",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/0/07/RandolphBurrow.png/revision/latest?cb=20130320231816",
                house = HouseName.RAVENCLAW,
            ),
            Player(
                id = UUID.fromString("f8f11664-a932-4e93-b93f-1d8ca4c0cf48"),
                firstName = "Jeremy",
                lastName = "Stretton",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                position = CHASER,
                favoriteSubject = "Alchemy",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/0/06/Jeremy_Stretton_Cleansweep_Seven.jpg/revision/latest?cb=20091020205540",
                house = HouseName.RAVENCLAW,
            ),
            Player(
                id = UUID.fromString("c2fe9d3a-140d-439d-9f15-2f48475eee51"),
                firstName = "Roger",
                lastName = "Davies",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                        1995,
                        1996,
                    ),
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/e/e5/Roger_Davies.jpg/revision/latest?cb=20180322052136",
                position = CHASER,
                favoriteSubject = "Apparition",
                house = HouseName.RAVENCLAW,
            ),
        )

    private fun hufflepuffTeam() =
        listOf(
            Player(
                id = UUID.fromString("4f9b53b6-b1c1-42d6-9217-2a0f39f010e3"),
                firstName = "Cedric",
                lastName = "Diggory",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                position = SEEKER,
                favoriteSubject = "Charms",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/9/90/Cedric_Diggory_Profile.png/revision/latest/scale-to-width-down/1000?cb=20161123045136",
                house = HouseName.HUFFLEPUFF,
            ),
            Player(
                id = UUID.fromString("fa680863-997d-4213-b9c3-d8839099bcfb"),
                firstName = "Herbert",
                lastName = "Fleet",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                position = KEEPER,
                favoriteSubject = "History of Magic",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/0/04/Herbert_Fleet.png/revision/latest?cb=20170304124757",
                house = HouseName.HUFFLEPUFF,
            ),
            Player(
                id = UUID.fromString("aa7fb66e-827f-42db-9aac-974c87b35504"),
                firstName = "Anthony",
                lastName = "Rickett",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                position = BEATER,
                imageUrl = "https://static.wikia.nocookie.net/harryalbuspotter/images/e/ea/Anthony_Rickett.PNG/revision/latest?cb=20120107004734",
                favoriteSubject = "Muggle Music",
                house = HouseName.HUFFLEPUFF,
            ),
            Player(
                id = UUID.fromString("adc01148-3d20-4dd6-a421-f87d784e58ac"),
                firstName = "Maxine",
                lastName = "O'Flaherty",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                position = BEATER,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/6/64/Maxine_O%27Flaherty.png/revision/latest?cb=20170304123914",
                favoriteSubject = "Muggle Art",
                house = HouseName.HUFFLEPUFF,
            ),
            Player(
                id = UUID.fromString("57b2d3d9-23a4-45ca-84ed-eb1154c34c07"),
                firstName = "Tamsin",
                lastName = "Applebee",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                position = CHASER,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/4/48/Tamsin_Applebee.png/revision/latest?cb=20170304124301",
                favoriteSubject = "Advanced Arithmancy Studies",
                house = HouseName.HUFFLEPUFF,
            ),
            Player(
                id = UUID.fromString("9651d59e-74da-43bd-b738-46a65097959b"),
                firstName = "Heidi",
                lastName = "Macavoy",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                favoriteSubject = "Muggle Art",
                position = CHASER,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/a/af/Heidi_Macavoy.png/revision/latest?cb=20170304123437",
                house = HouseName.HUFFLEPUFF,
            ),
            Player(
                id = UUID.fromString("757c624c-40e3-4e9f-a4a8-40cd09839c8f"),
                firstName = "Malcolm",
                lastName = "Preece",
                yearsPlayed =
                    listOf(
                        1993,
                        1994,
                    ),
                favoriteSubject = "Ghoul Studies",
                position = CHASER,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/9/92/Malcolm_Preece.png/revision/latest?cb=20170304122953",
                house = HouseName.HUFFLEPUFF,
            ),
        )

    /*** positions ***/

    private const val CHASER = 1
    private const val BEATER = 2
    private const val KEEPER = 3
    private const val SEEKER = 4

    val mockPositions =
        mapOf(
            1 to Position(positionName = "Chaser"),
            2 to Position(positionName = "Beater"),
            3 to Position(positionName = "Keeper"),
            4 to Position(positionName = "Seeker"),
        )

    val mockPositionsResponseWrapper =
        ResponseWrapper(
            data = mockPositions,
            statusCode = HttpURLConnection.HTTP_OK,
        )

    val mockPositionsResponseSuccess = Response.success(mockPositionsResponseWrapper)

    val mockPositionsDeferredResponseSuccess =
        CompletableDeferred(
            mockPositionsResponseSuccess,
        )

    /*** players entity ***/

    val mockPlayersEntities: List<PlayerEntity> = mockAllQuidditchTeam.toPlayersEntities(mockPositions)

    /*** players and positions ***/

    val mockRavenclawPlayersAndPositionsResponseWrappers =
        ResponseWrappers(
            responseWrapper1 = mockRavenclawPlayersResponseWrapper,
            responseWrapper2 = mockPositionsResponseWrapper,
        )

    val mockRavenclawPlayersAndPositionsResponseSuccess =
        Response.success(mockRavenclawPlayersAndPositionsResponseWrappers)

    val mockRavenclawPlayersAndPositionsDeferredResponseSuccess =
        CompletableDeferred(
            mockRavenclawPlayersAndPositionsResponseSuccess,
        )

    /*** status ***/

    fun mockStatus(): Status {
        val player = ravenclawTeam().first()
        val name = "$player.firstName $player.lastName"
        return Status(
            playerId = player.id,
            status = getStatus(name),
        )
    }

    val mockStatusResponseWrapper =
        ResponseWrapper(
            data = mockStatus(),
            statusCode = HttpURLConnection.HTTP_OK,
        )

    val mockStatusResponseSuccess = Response.success(mockStatusResponseWrapper)

    val mockStatusDeferredResponseSuccess =
        CompletableDeferred(
            mockStatusResponseSuccess,
        )

    fun getStatus(name: String) = String.format("%s is breaking into the Ministry of Magic %s", name, "\uD83D\uDD2E")
}

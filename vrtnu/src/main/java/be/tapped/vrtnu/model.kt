package be.tapped.vrtnu

import arrow.core.NonEmptyList
import be.tapped.vrtnu.content.Category
import be.tapped.vrtnu.content.Episode
import be.tapped.vrtnu.content.Program
import be.tapped.vrtnu.content.StreamInformation
import be.tapped.vrtnu.epg.Epg
import be.tapped.vrtnu.profile.FavoriteWrapper
import be.tapped.vrtnu.profile.LoginFailure
import be.tapped.vrtnu.profile.TokenWrapper
import be.tapped.vrtnu.profile.VRTPlayerToken
import be.tapped.vrtnu.profile.XVRTToken
import okhttp3.Request

sealed class ApiResponse {
    sealed class Success : ApiResponse() {
        data class Programs(val programs: List<Program>) : Success()
        data class SingleProgram(val program: Program) : Success()
        data class Categories(val categories: List<Category>) : Success()
        data class Episodes(val episodes: List<Episode>) : Success()
        data class StreamInfo(val info: StreamInformation) : Success()
        data class ProgramGuide(val epg: Epg) : Success()

        data class Token(val tokenWrapper: TokenWrapper) : Success()
        data class PlayerToken(val vrtPlayerToken: VRTPlayerToken) : Success()
        data class VRTToken(val xVRTToken: XVRTToken) : Success()
        data class Favorites(val favorites: FavoriteWrapper) : Success()
    }

    sealed class Failure : ApiResponse() {
        data class NetworkFailure(val responseCode: Int, val request: Request) : Failure()
        data class JsonParsingException(val throwable: Throwable) : Failure()
        object EmptyJson : Failure()

        data class FailedToLogin(val loginResponseFailure: LoginFailure) : Failure()
        data class MissingCookieValues(val cookieValues: NonEmptyList<String>) : Failure()
    }
}
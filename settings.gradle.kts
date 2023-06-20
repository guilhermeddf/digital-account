rootProject.name = "digital-account"

include("application")
include("domain")

include("launcher")

include("infrastructure:postgres")
include("infrastructure:dynamodb")
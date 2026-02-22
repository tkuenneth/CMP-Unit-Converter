import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        // Kotlin/Native exports Unit-returning top-level functions with a "do" prefix in Swift/Obj-C.
        // Shared code has initKoin(); the generated API is doInitKoin(). See Koin CMP quickstart:
        // https://insert-koin.io/docs/quickstart/cmp
        KoinAppKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

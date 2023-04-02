/*//Использовать если не собирается проект (ошибка : java.lang.IllegalArgumentException:
// project.classLoaderScope must be locked before querying the project schema или ошибка:
// java.lang.IllegalArgumentException: org.gradle.api.internal.initialization.DefaultClassLoaderScope@70887084
// must be locked before it can be used to compute a classpath!).
// Источник: https://github.com/gradle/gradle/issues/4823
//buildscript{
//    val startParameter = gradle.startParameter;
//    if(startParameter.isConfigureOnDemand) {
//        subprojects.forEach {s ->
//            if(s.buildscript.sourceFile!!.name.endsWith(".kts")){ //kotlin project
//                var current = s
//                var depPath = current.path
//                while(current.path != rootProject.path && current.parent!!.path != rootProject.path) {
//                    depPath += " -> ${current.parent!!.path}"
//                    current = current.evaluationDependsOn(current.parent!!.path)
//                }
//                logger.info("Adding kotlin project dependency for CoD: ${depPath}")
//            }
//        }
//    }
//}
*/
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
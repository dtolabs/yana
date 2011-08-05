includeTargets << grailsScript("Init")

PANDOC = "pandoc"        // The pandoc executable
DIST = "target/docs"     // Build target dir


target(main: "Builds the Yana documentation") {
    ant.echo ( message: 'Building Yana documentation ....' )
    depends ( clean, expand, html )
}

target(clean: "Cleans the documentation target directory") {
    ant.delete( dir: DIST )
}

target(expand: "Expand templates" ) {
    ant.property (file: 'docs/version.properties')
    ant.echo ( message: 'Expanding templates for documentation version: ${version.number}' )
    ant.copy (file: 'docs/templates/title.txt.template',
              tofile: 'docs/title.txt', filtering: true) 
    ant.copy (file: "docs/templates/docs.css", 
              todir: "${DIST}/html", filtering: true )
}

target(figures: "Process the figures" ) {
   ant.echo(message: "Processing figures...")
   ant.mkdir( dir: "${DIST}/html/figures" )
   ant.copy(todir: "${DIST}/html/figures") {
     fileset(dir: "docs/figures") {
       include(name: "*.png")
     }
   }
}


target(html: "Generates the HTML pages" ) {
   ant.mkdir ( dir: "${DIST}/html" )
   depends( figures )
   ant.echo ( message: 'Generating the User-Guide ...' )
   ant.exec ( executable: PANDOC ) {
     arg ( value: "--number-sections" )
     arg ( value: "--toc" )
     arg ( value: "-s" )
     arg ( value: "docs/title.txt" )
     arg ( value: "docs/en/guide/01-introduction.md" )
     arg ( value: "docs/en/guide/02-getting-started.md" )
     arg ( value: "docs/en/guide/03-beginning.md" )
     arg ( value: "--css=docs.css" )
     arg ( value: "--template=docs/templates/html.template" )
     arg ( value: "--include-before=docs/templates/before.html" )
     arg ( value: "--include-after=docs/templates/after.html" )
     arg ( value: "-o" )
     arg ( value: "${DIST}/html/User-Guide.html" )
   }
   ant.echo ( message: "Completed: ${DIST}/html/User-Guide.html")

   ant.echo ( message: 'Generating the API-Manual ...' )
   ant.exec ( executable: PANDOC ) {
     arg ( value: "--number-sections" )
     arg ( value: "--toc" )
     arg ( value: "-s" )
     arg ( value: "docs/title.txt" )
     arg ( value: "docs/en/api/01-chapter.md" )
     arg ( value: "--css=docs.css" )
     arg ( value: "--template=docs/templates/html.template" )
     arg ( value: "--include-before=docs/templates/before.html" )
     arg ( value: "--include-after=docs/templates/after.html" )
     arg ( value: "-o" )
     arg ( value: "${DIST}/html/API-Manual.html" )
   }
   ant.echo ( message: "Completed: ${DIST}/html/API-Manual.html" )

   ant.echo ( message: 'Generating reference pages ...' )
   ant.exec ( executable: PANDOC ) {
     arg ( value: "--number-sections" )
     arg ( value: "--toc" )
     arg ( value: "-s" )
     arg ( value: "docs/title.txt" )
     arg ( value: "docs/en/manpages/man5/node-v10.md" )
     arg ( value: "--css=docs.css" )
     arg ( value: "--template=docs/templates/html.template" )
     arg ( value: "--include-before=docs/templates/before.html" )
     arg ( value: "--include-after=docs/templates/after.html" )
     arg ( value: "-o" )
     arg ( value: "${DIST}/html/node-v10.html" )
   }
   ant.echo ( message: "Completed: ${DIST}/html/node-v10.html")
}

setDefaultTarget(main)

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

target(html: "Generates the HTML pages" ) {
   ant.mkdir ( dir: "${DIST}/html" )

   ant.echo ( message: 'Generating API manual ...' )
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

   ant.echo ( message: 'Generating Unix manual pages ...' )
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

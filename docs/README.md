Requirements: pandoc

$(PANDOC) --number-sections --toc -s title.txt 0[0-9]-*/*.md 1[0-9]-*/*.md -o $(DIST)/html/RunDeck-Guide.html

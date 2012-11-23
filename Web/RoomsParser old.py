#!/usr/bin/env python

from bs4 import BeautifulSoup
import urllib2

CELLS_IN_ROW = 9
ROOMS_URL = r"http://gezer.bgu.ac.il/compclass/compclass.php"

class RoomsParser(object):
    def __init__(self):
        self.html = None
    def getHtml(self):
        self.html = urllib2.urlopen(ROOMS_URL).read()
        return self.html
    def feed(self, html):
        h1 = hash(html)
        html = html.replace("<table><tr><td>", "")
        html = html.replace("</td><td>-</td><td>", "-")
        html = html.replace("<td> </tr></table ", "")
        if h1 == hash(html):
            raise "ERROR!"
        self.soup = BeautifulSoup(html)
    def getRooms(self):
        if self.html is None:
            self.feed(self.getHtml())
        result = []
        tr = self.soup.table.tr
        tr = tr.next
        while tr != None:
            if len(tr) == 5 and tr.string is None:
                print "tr"
                room = []
                td = tr.td
                while td != None:
                    text = td.string
                    if text is None:
                        if not td.a is None:
                            text = td.a.get("href")
                        else:
                            text = td.get_text()
                    if not text is None and len(text) > 1:
                        if len(room) == 0 or text.replace(" ", "") != room[-1].replace(" ", ""):
                            room.append(text)
                    td = td.next
                result.append(room[:5])
            tr = tr.next
        return result

if __name__ == "__main__":
    rp = RoomsParser()
    #rp.feed(open("compclass.htm", "rb").read())
    r = rp.getRooms()
    of = open("comclassout.htm", "wb")
    #print r
    for i in r:
        print i
        of.write(i.__repr__())
        #for f in i:
        #    print f
            #f = unicode(f.strip(codecs.BOM_UTF8), 'uft-8')
            #of.write(f)
        #of.write("\n")

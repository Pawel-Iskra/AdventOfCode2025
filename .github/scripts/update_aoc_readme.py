import os
import re
import urllib.request

from datetime import datetime, timedelta
from zoneinfo import ZoneInfo

session = os.environ["AOC_SESSION"]

print("Session length:", len(session))
print("Contains whitespace:", any(c.isspace() for c in session))

year = 2025
url = f"https://adventofcode.com/{year}/leaderboard/self"

request = urllib.request.Request(
    url,
    headers={
        "Cookie": f"session={session}",
        "User-Agent": "AoC README updater"
    }
)

with urllib.request.urlopen(request) as response:
    html = response.read().decode("utf-8")

start = html.find("<pre>")
end = html.find("</pre>")

table = html[start:end]


for line in table.splitlines():
    parts = line.split()

    if not parts or not parts[0].isdigit():
        continue

    day = int(parts[0])
    part1 = parts[1]
    part2 = parts[2]

    print(day, part1, part2)


POLAND = ZoneInfo("Europe/Warsaw")
AOC_TZ = ZoneInfo("America/New_York")
aoc_start = datetime(2025, 12, day, 0, 0, 0, tzinfo=AOC_TZ)

completed_at = aoc_start + timedelta(
    hours=hours,
    minutes=minutes,
    seconds=seconds
)

completed_at = completed_at.astimezone(POLAND)
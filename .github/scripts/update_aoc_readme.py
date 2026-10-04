import os
import urllib.request

from datetime import datetime, timedelta
from zoneinfo import ZoneInfo


session = os.environ["AOC_SESSION"]
year = 2025
POLAND = ZoneInfo("Europe/Warsaw")
AOC_TZ = ZoneInfo("America/New_York")
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


def parse_duration(duration):
    if duration in ("-", "&gt;24h", ">24h"):
        return None

    hours, minutes, seconds = map(int, duration.split(":"))
    return timedelta(hours=hours, minutes=minutes, seconds=seconds)


for line in table.splitlines():
    parts = line.split()

    if not parts or not parts[0].isdigit():
        continue

    day = int(parts[0])
    part1 = parts[1]
    part2 = parts[2]

    duration = parse_duration(part1)

    if duration is None:
        completed_at = part1
    else:
        aoc_start = datetime(
            year, 12, day,
            0, 0, 0,
            tzinfo=AOC_TZ
        )

        completed_at = (
                aoc_start + duration
        ).astimezone(POLAND)

    print(day, part1, "->", completed_at)
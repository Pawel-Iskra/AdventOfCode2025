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


readme_rows = []

for line in table.splitlines():
    parts = line.split()

    if not parts or not parts[0].isdigit():
        continue

    day = int(parts[0])
    part1 = parts[1]
    part2 = parts[2]

    def get_completed_at(duration_text):
        duration = parse_duration(duration_text)

        if duration is None:
            return duration_text.replace("&gt;", ">")

        aoc_start = datetime(
            year, 12, day,
            0, 0, 0,
            tzinfo=AOC_TZ
        )

        return (
                aoc_start + duration
        ).astimezone(POLAND).strftime("%Y-%m-%d %H:%M:%S")

    part1_date = get_completed_at(part1)
    part2_date = get_completed_at(part2)

    readme_rows.append(
        f"| {day:02d} | {part1_date} | {part2_date} |"
    )

readme_table = """| Day | Part 1 | Part 2 |
|---:|---|---|
""" + "\n".join(readme_rows)

print(readme_table)
